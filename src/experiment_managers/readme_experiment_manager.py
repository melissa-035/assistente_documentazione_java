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

def save_experiment(experiment_dir, prompt, response, metadata):

    prompt_file = experiment_dir / "prompt.txt"
    output_file = experiment_dir / "output.md"
    metadata_file = experiment_dir / "metadata.json"

    with open(prompt_file, "w", encoding="utf-8") as file:
        file.write(prompt)

    with open(output_file, "w", encoding="utf-8") as file:
        file.write(response)

    with open(metadata_file, "w", encoding="utf-8") as file:
        json.dump(
            metadata,
            file,
            indent=4
        )
