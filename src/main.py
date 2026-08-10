from src.tasks.readme_task import readme_task
from src.tasks.javadoc_task import javadoc_task


def main():

    task = "readme"
    repository_name = "mp3-player"
    model_name = "gemma3_4b"
    prompt_type = "chain_of_thought"

    if task == "readme":
        readme_task(
            repository_name,
            model_name,
            prompt_type
        )

    elif task == "javadoc":
        javadoc_task(
            repository_name,
            model_name,
            prompt_type
        )


if __name__ == "__main__":
    main()