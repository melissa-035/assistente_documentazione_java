import os


def read_repository(path):
    files = {}

    for root, dirs, filenames in os.walk(path):

        # ignora la cartella .git
        if ".git" in root:
            continue

        for filename in filenames:

            file_path = os.path.join(root, filename)

            try:
                with open(file_path, "r", encoding="utf-8") as file:
                    files[file_path] = file.read()

            except UnicodeDecodeError:
                # ignora file binari (immagini, ecc.)
                pass

    return files