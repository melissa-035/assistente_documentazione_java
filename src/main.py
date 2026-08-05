import time
from importlib.metadata import metadata
from ollama_client import ask_model
from repository_reader import read_repository
from prompt_builder import build_prompt
from experiment_manager import save_experiment
from code_analyzer import analyze_repository
from experiment_manager import create_experiment_directory


def main():

    repository_path = "repository/mp3-player"
    repository_name = "mp3-player"
    model_name = "gemma3:4b"

    experiment_dir = create_experiment_directory(
        repository_name,
        model_name
    )

    files = read_repository(repository_path, extensions=[".java"])
    print(f"File trovati: {len(files)}")

    structure = analyze_repository(files)

    for file_path in files:
        print(file_path)

    prompt = build_prompt(structure)

    start_time =time.time()

    response = ask_model(prompt)

    end_time = time.time()

    runtime = end_time - start_time

    metadata = {
        "repository": repository_name,
        "model": model_name,
        "files_analyzed": len(files),
        "prompting_technique": "role prompting",
        "runtime_seconds": runtime
    }

    save_experiment(
        experiment_dir,
        prompt,
        response,
        metadata
    )


if __name__ == "__main__":
    main()
