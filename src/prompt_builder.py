def build_prompt(project_structure, prompt_file):

    with open(prompt_file, "r", encoding="utf-8") as file:

        prompt = file.read()


    for filename, info in project_structure.items():
        prompt += f"""

--------------------
FILE: {filename}
--------------------

Class:
{info["class_name"]}

Fields:
"""

        for field in info["fields"]:
            prompt += f"- {field}\n"


        prompt += """

Methods:
"""

        for method in info["methods"]:
            prompt += f"- {method}\n"


    return prompt