import os

def read_repository(path, extensions=None):
    files = {}

    for root, dirs, filenames in os.walk(path):

        #ignora la cartella .git
        if ".git" in root:
            continue

        for filename in filenames:

            #filtro estensioni
            if extensions is not None:
                if not any(filename.endswith(ext) for ext in extensions):
                    continue

            file_path = os.path.join(root, filename)

            relative_path = os.path.relpath(file_path, path)

            try:
                with open(file_path, "r", encoding="utf-8") as file:
                    files[relative_path] = file.read()

            except UnicodeDecodeError:
                # ignora file binari (immagini, ecc.)
                pass

    return files