## Generate code from json schema

To follow the dry principle, we can generate code from the json schema. This way we can ensure that the api contract is always "up to date" with the code.

[plugin-source](https://github.com/pwall567/json-kotlin-gradle?tab=readme-ov-file#to-use)

The plugin follows the principle of “convention over configuration”. The default location for the schema file or files to be input to the generation process is:

```
/src/main/resources/schema
```
