import time
from src.ollama_client import ask_model
from src.repository_reader import read_repository
from src.prompt_builders.javadoc_prompt_builder import build_prompt
from src.experiment_manager import save_experiment
from src.experiment_manager import create_experiment_directory
from datetime import datetime
from config.config import REPOSITORY_DIR
from config.config import PROMPTS_DIR


def javadoc_task():

    repository_path = (REPOSITORY_DIR/"mp3-player")
    repository_name = "mp3-player"
    model_name = "gemma3_4b"
    prompt_type = "zero_shot"

    prompt_file = (PROMPTS_DIR/"javadoc_prompts"/f"{prompt_type}.txt")

    files = read_repository(repository_path, extensions=[".java"])
    print(f"File trovati: {len(files)}")

    files = {
        path: content
        for path, content in files.items()
        if path.endswith("Track.java")
    }

    print(f"File trovati: {len(files)}")

    for file_path in files:
        print(file_path)

    prompt = build_prompt(files, prompt_file)

    print("Prompt costruito")
    print(f"Lunghezza prompt: {len(prompt)} caratteri")

    start_time =time.time()

    response = ask_model(prompt)

    print("Risposta ricevuta")

    end_time = time.time()

    runtime = end_time - start_time

    date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    metadata = {
        "task": "javadoc",
        "date": date,
        "LLM_runtime_seconds": runtime,
        "repository": repository_name,
        "model": model_name,
        "prompt_type": prompt_type,
        "prompt_file": prompt_file,
        "prompt_length": len(prompt),
        "response_length": len(response),
        "files_analyzed": len(files),
    }

    print("Creo directory esperimento")

    experiment_dir = create_experiment_directory(
        repository_name,
        model_name
    )

    print(experiment_dir)

    save_experiment(
        experiment_dir,
        prompt,
        response,
        metadata
    )

    print("Esperimento salvato")