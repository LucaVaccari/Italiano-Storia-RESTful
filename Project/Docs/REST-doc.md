# KSV service

This REST service provides the HTTP CRUD method to access the ksv-links database.

## GET

The GET method returns an array of results based on the queries.

### Queries

There are different allowed queries that can be used to filter the results. Any non allowed query will be ignored. If no
query is provided all the information is returned. Queries are case-insensitive.

A query follows this format: &lt;name&gt;=&lt;value&gt;. Queries can be concatenated with '&'.

##### On authors

- firstname: the first name of the author
- lastname: the last name of the author
- birthyear: the year of birth of the author
- deathyear: the year of death of the author
- lifeyear: any year in which the author was alive
  (for example 1904 is valid for an author born in 1888 and dead in 1970)

##### On topics

- name: the name of the topic (spaces can be included by surrounding the value with " or can be replaced by '-')
- year: any year in which the topic was happening
- place: the place of the topic (formatted as the name)

### Allowed URLs

- /authors?queries
- /authors/:id
- /topics?queries
- /topics/:id

## POST

The POST method adds new information in the database. The information must be passed in the body of the request in JSON
and must be well-formed.

### Allowed URLs

- /authors
- /topics

## PUT

The PUT method updates existing information in the database. The information must be passed in the body of the request
in JSON and must be well-formed.

### Allowed URLs

- /authors/:id
- /topics/:id

## DELETE

The DELETE method deletes information from the database.

### Allowed URLs

- /authors/:id
- /topics/:id
