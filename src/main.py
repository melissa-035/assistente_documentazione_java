import time
from ollama_client import ask_model
from repository_reader import read_repository
from prompt_builder import build_prompt
from experiment_manager import save_experiment
from code_analyzer import analyze_repository
from experiment_manager import create_experiment_directory
from datetime import datetime


def main():

    repository_path = "repository/mp3-player"
    repository_name = "mp3-player"
    model_name = "gemma3_4b"
    prompt_type = "role_prompting"

    prompt_file = f"prompts/{prompt_type}.txt"


    experiment_dir = create_experiment_directory(
        repository_name,
        model_name
    )

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

    save_experiment(
        experiment_dir,
        prompt,
        response,
        metadata
    )


if __name__ == "__main__":
    main()
