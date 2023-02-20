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


**Logic Behind the Solution**

Recursively parse the JSON object to flatten and reach the leaf nodes which will then be individually processed according to their
data type i.e Process a Number Transform according to the methods set in the NumberTransform Model or process a String transform according to criteria set by StringTransform Model.

While we we recursively iterate the JSON object we also store the ancestor history to build a set of QUEUE based operation to follow 
For example

````
{
  "list_3": {
    "L": [
      {
        "N": "777"
      },
      {
        "N": "0666"
      }
    ]
  }
}
````

We recursively reach leafnode "N" - "777" and also have key as list_3.L.N#1 created to map out the keys in order from root to leafnode

Now this object will be transformed according to last operation in key N#0 where N is the operation (Number transform) and # is separator followed by number used to make it unique since there is 1 more N present with value 0666

After this we create a set of queue operations to be performed for list_3.L.N#1

N -> Number Transform

L -> create a list transform

list-3 -> store the result till not under key "list-3" and create a new JSON object 

We keep proceeding further until entire unique key is processed for all the operations uptil the root node "list_3" in this case.

Same is followed for "N" : "0666" and we get the end result in JSON ARRAY


[{"list_3":[666,777]}]


All the transformation criteria are applied according to respective Transformation Models (Number,String,Null etc) who implement the Transformer Interface. 

