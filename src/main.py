from ollama_client import ask_model
from repository_reader import read_repository
from prompt_builder import build_prompt

def main():

    repository_path = "repository/mp3-player"
    files = read_repository(repository_path)
    print(f"File trovati: {len(files)}")

    prompt = build_prompt(files)

    with open("output/prompt.txt", "w", encoding="utf-8") as file:
        file.write(prompt)

    response = ask_model(prompt)

    with open("output/README.md", "w", encoding="utf-8") as file:
        file.write(response)


if __name__ == "__main__":
    main()
