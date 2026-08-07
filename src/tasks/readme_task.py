import time
from src.ollama_client import ask_model
from src.repository_reader import read_repository
from src.prompt_builders.readme_prompt_builder import build_prompt
from src.experiment_manager import save_experiment
from src.readme_code_analyzer import analyze_repository
from src.experiment_manager import create_experiment_directory
from datetime import datetime
from config.config import REPOSITORY_DIR
from config.config import PROMPTS_DIR


def readme_task():

    repository_path = (REPOSITORY_DIR/"mp3-player")
    repository_name = "mp3-player"
    model_name = "gemma3_4b"
    prompt_type = "chain_of_thought"

    prompt_file =(PROMPTS_DIR/"readme_prompts"/f"{prompt_type}.txt")

    files = read_repository(repository_path, extensions=[".java"])
    print(f"File trovati: {len(files)}")

    structure, stats = analyze_repository(files)

    for file_path in files:
        print(file_path)

    prompt = build_prompt(structure, prompt_file)

    start_time =time.time()

    response = ask_model(prompt)

    end_time = time.time()

    runtime = end_time - start_time

    date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    metadata = {
        "task": "readme",
        "date": date,
        "LLM_runtime_seconds": runtime,
        "repository": repository_name,
        "model": model_name,
        "prompt_type": prompt_type,
        "prompt_file": prompt_file,
        "prompt_length": len(prompt),
        "response_length": len(response),
        "files_analyzed": len(files),
        **stats
    }

    experiment_dir = create_experiment_directory(
        repository_name,
        model_name
    )

    save_experiment(
        experiment_dir,
        prompt,
        response,
        metadata
    )
