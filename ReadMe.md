Welcome to **elasticJSON**.

This Project serves as a utility to tranform an input json into the required output json following all the required transformation Criteria.

Input Json:

````
{
  "number_1": {
    "N": "1.50"
  },
  "string_1": {
    "S": "784498 "
  },
  "string_2": {
    "S": "2014-07-16T20:55:46Z"
  },
  "map_1": {
    "M": {
      "bool_1": {
        "BOOL": "truthy"
      },
      "null_1": {
        "NULL ": "true"
      },
      "list_1": {
        "L": [
          {
            "S": ""
          },
          {
            "N": "011"
          },
          {
            "N": "5215s"
          },
          {
            "BOOL": "f"
          },
          {
            "NULL": "0"
          }
        ]
      }
    }
  },
  "list_2": {
    "L": "noop"
  },
  "list_3": {
    "L": [
      "noop"
    ]
  },
  "": {
    "S": "noop"
  }
}
````

**Running instructions**

(you need gradle on CLI to run and build this project so install gradle 
using
_**brew install gradle**_
)
Download the zip from https://github.com/HarshGajraSUNY/elasticJSON

Using _**git clone**_ https://github.com/HarshGajraSUNY/elasticJSON.git

````
RUN INSTRUCTIONS

cd elasticJSON
gradle build
gradle run

That's it! you will see the output transformed json array in stdout.
````

**Acceptance criteria**

Submission is **complete** with all the required criteria except the map structure is not lexically sorted


**Time Taken to run**

The code takes 0.082 seconds to execute and transform the input json as per profiling.
