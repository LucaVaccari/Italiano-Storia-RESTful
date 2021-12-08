### GET

#### authors

Request format: <URL>/authors?queries Queries:

- firstname: the first name of the author
- lastname: the last name of the author
- birthyear: the year of birth of the author
- deathyear: the year of death of the author
- lifeyear: any year in which the author was alive (for example 1912 is valid for an author born in 1888 and dead in
    1970)

#### topics

Request format: <URL>/topics?queries Queries:

- name: the name of the topic (spaces must be replaced with dashes '-')
- year: any year in which the topic is (for example a topic that starts in 1914 and finishes in 1918 would be found with
    1917)
- place: the place of the topic (spaces must be replaced with dashes '-')

### POST

#### authors

Request format: <URL>/authors

Request body: JSON of the author to add which must have the following properties:

- firstName
- lastName
- birthDate
- deathDate
- life

#### topics

Request format: <URL>/topics

Request body: JSON of the topic to add which must have the following properties:

- name
- startdate
- enddate
- description
- place

### PUT

#### authors

#### topics

### DELETE

#### authors

#### topics