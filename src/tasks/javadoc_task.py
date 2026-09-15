import time
from datetime import datetime

from src.ollama_client import ask_model
from src.repository_reader import read_repository
from src.prompt_builders.javadoc_prompt_builder import build_prompt
from src.experiment_managers.javadoc_experiment_manager import (
    create_experiment_directory,
    save_javadoc_file,
    save_metadata
)

from config.config import REPOSITORY_DIR
from config.config import PROMPTS_DIR


def javadoc_task(repository_name, model_name, prompt_type):

    repository_path = REPOSITORY_DIR / repository_name

    prompt_file = (
        PROMPTS_DIR /
        "javadoc_prompts" /
        f"{prompt_type}.txt"
    )

    files = read_repository(
        repository_path,
        extensions=[".java"]
    )

    print(f"File trovati: {len(files)}")

    experiment_dir = create_experiment_directory(
        repository_name,
        model_name
    )

    file_metadata = {}
    total_start_time = time.time()

    for file_path, content in files.items():

        print(f"Elaborazione: {file_path}")

        single_file = {
            file_path: content
        }

        prompt = build_prompt(
            single_file,
            prompt_file
        )

        start_time = time.time()

        response = ask_model(prompt)

        end_time = time.time()

        runtime = end_time - start_time

        print(f"Risposta ricevuta per {file_path}")
        print(f"Tempo di generazione: {runtime:.2f} secondi")

        save_javadoc_file(
            experiment_dir,
            file_path,
            prompt,
            response
        )

        file_metadata[file_path] = {
            "runtime_seconds": runtime,
            "prompt_length": len(prompt),
            "response_length": len(response)
        }

    total_end_time = time.time()

    total_runtime = total_end_time - total_start_time

    metadata = {
        "task": "javadoc",
        "date": datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        "repository": repository_name,
        "model": model_name,
        "prompt_type": prompt_type,
        "prompt_file": str(prompt_file),
        "files_analyzed": len(files),
        "total_runtime_seconds": total_runtime,
        "files": file_metadata
    }

    save_metadata(
        experiment_dir,
        metadata
    )

    print("Esperimento salvato")