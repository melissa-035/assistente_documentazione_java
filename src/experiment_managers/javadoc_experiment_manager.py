from pathlib import Path
from config.config import EXPERIMENTS_DIR
import json


def create_experiment_directory(repository_name, model_name):

    base_dir = (
        EXPERIMENTS_DIR /
        repository_name /
        model_name
    )

    base_dir.mkdir(parents=True, exist_ok=True)

    existing = [
        folder for folder in base_dir.iterdir()
        if folder.name.startswith("experiment_")
    ]

    experiment_id = len(existing) + 1

    experiment_dir = base_dir / f"experiment_{experiment_id:03d}"

    experiment_dir.mkdir()

    return experiment_dir


def save_javadoc_file(experiment_dir, file_path, prompt, response):

    prompts_dir = experiment_dir / "prompts"
    outputs_dir = experiment_dir / "outputs"

    prompts_dir.mkdir(exist_ok=True)
    outputs_dir.mkdir(exist_ok=True)

    filename = Path(file_path).name

    prompt_file = prompts_dir / f"{filename}.txt"
    output_file = outputs_dir / filename

    with open(prompt_file, "w", encoding="utf-8") as file:
        file.write(prompt)

    with open(output_file, "w", encoding="utf-8") as file:
        file.write(response)


def save_metadata(experiment_dir, metadata):

    metadata_file = experiment_dir / "metadata.json"

    with open(metadata_file, "w", encoding="utf-8") as file:
        json.dump(
            metadata,
            file,
            indent=4
        )