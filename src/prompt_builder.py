def build_prompt(project_structure):

    prompt = """
You are a software documentation assistant.

Your task is to generate a README.md file for a Java repository.

Your output MUST be only Markdown documentation.

Do NOT:
- write Java code
- suggest code improvements
- find bugs
- provide code examples
- modify or rewrite source code

Generate documentation based only on the extracted structure of the source code.

The README must contain:

1. Project name
2. General project description
3. Repository structure
4. Description of the main Java classes
5. Implemented features
6. Dependencies (only if they can be identified from the source code)

Important:
- Do not invent classes, files, or features.
- Use only information explicitly present in the provided source code.
- If some information is unavailable, state that it is not available.

Repository source code:

"""

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