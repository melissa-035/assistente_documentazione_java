def build_prompt(files):

    prompt = """
Sei un assistente per la documentazione software.

Analizza la seguente repository Java e genera un README completo.

Il README deve contenere:
- descrizione del progetto
- struttura del progetto
- requisiti
- installazione
- utilizzo
- eventuali note tecniche

Ecco i file della repository:

"""

    for filename, content in files.items():
        prompt += f"""

--------------------
FILE: {filename}
--------------------

{content}

"""

    return prompt