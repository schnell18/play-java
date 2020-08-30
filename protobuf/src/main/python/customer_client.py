#!/usr/bin/env python

import urllib.request

import customer_pb2

if __name__ == '__main__':
    customer = customer_pb2.Customer()
    customers_read = urllib.request.urlopen('http://localhost:8080/v1/proto/customers/2').read()
    customer.ParseFromString(customers_read)
    print(customer)

    customers = customer_pb2.Customers()
    customers_read = urllib.request.urlopen('http://localhost:8080/v1/proto/customers').read()
    customers.ParseFromString(customers_read)
    print(customers)
