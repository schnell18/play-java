# Introduction

This project explores cloud-native application development w/ spring.

## Generate Python code

You may generate the customer model for Python as:

    cd src/main/bash
    source proto-gen.sh
    gen python

Install the `protobuf` Python library as:

    cd src/main/python
    pip install -r requirements.txt

## Test w/ Python client

The CustomerController creates three seed customers on start.
You may try the protobuf content type w/ Python client by:

    cd src/main/python
    python customer_client.py

## Access the H2 database console

Click [this link][1] to access the H2 database web console.

[1]: http://localhost:8080/h2-console/