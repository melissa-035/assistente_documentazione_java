import re


def analyze_repository(files):

    analyzed = {}

    for file_path, content in files.items():

        if not file_path.endswith(".java"):
            continue

        class_name = extract_class_name(content)

        fields = extract_fields(content)

        methods = extract_methods(content)

        analyzed[file_path] = {
            "class_name": class_name,
            "fields": fields,
            "methods": methods
        }

    stats = {
        "files_analyzed": len(analyzed),
        "classes": len(analyzed),
        "methods": sum(
            len(info["methods"])
            for info in analyzed.values()
        ),
        "fields": sum(
            len(info["fields"])
            for info in analyzed.values()
        )
    }

    return analyzed, stats



def extract_class_name(content):

    match = re.search(
        r"class\s+(\w+)",
        content
    )

    if match:
        return match.group(1)

    return None



def extract_fields(content):

    fields = []

    matches = re.findall(
        r"private\s+([\w<>]+)\s+(\w+);",
        content
    )

    for field_type, name in matches:
        fields.append(
            f"{name}: {field_type}"
        )

    return fields



def extract_methods(content):

    methods = []

    matches = re.findall(
        r"public\s+[\w<>\[\]]+\s+(\w+\([^)]*\))",
        content
    )

    methods.extend(matches)

    return methods