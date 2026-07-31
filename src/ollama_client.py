import ollama

def ask_model(prompt):
    response = ollama.chat(
        model="gemma3:4b",
        messages=[{
            "role": "user",
            "content": prompt
        }

        ]
    )
    return response["message"]["content"]