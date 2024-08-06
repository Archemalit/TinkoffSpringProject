# T-Fintech (Java Developer)
#### Project: The translator's website uses the API of a third-party service.
```My contacts: Telegram: @kiz_zyaka```
___
__Project database schema:__

![ER-diagram](https://github.com/Archemalit/TinkoffSpringProject/blob/develop/database.jpg)
___
# Installation

**1. Clone a repository**

```
git clone https://github.com/Archemalit/TinkoffSpringProject.git 
```

**2. Load Maven script in the file ```pom.xml```**

**3. Change the values in the ```application.yaml``` file for your database:**

```
spring:
    datasource:
        url: YOUR_DATABASE_URL
        username: YOUR_DATABASE_USERNAME
        password: YOUR_DATABASE_PASSWORD
```

**5. Run the file ```TinkoffSpringProjectApplication```**

**6. Go to ```http://localhost:8080```**

# About the Project

To translate the text, you need to select the original language, the translation language and the separator. Each word will be translated in a separate stream using the Googleapis API. \
To get the result, you need to click on the **"translate"** button, after which the translated text will appear below.
