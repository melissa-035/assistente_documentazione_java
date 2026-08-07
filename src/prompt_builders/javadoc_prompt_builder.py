def build_prompt(files, prompt_file):

    with open(prompt_file, "r", encoding="utf-8") as file:

        prompt = file.read()


    for filename, content in files.items():
        prompt += f"""
        
--------------------
FILE: {filename}
--------------------

{content}

"""
    return prompt