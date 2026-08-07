from tasks.javadoc_task import javadoc_task
from tasks.readme_task import readme_task


def main():

    task = "readme"


    if task == "readme":
        readme_task()

    elif task == "javadoc":
        javadoc_task()

if __name__ == "__main__":
    main()