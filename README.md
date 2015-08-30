Task 3 - 
Adding support the calculation based on DFS algorithm

The main problem:
Distribution of tasks to multiple computers

The solution:
The server receives graph with edges, he use the function “Minimal Cliques” that calculate the edges from graph. 
Then the server sends each time edge to one of the computers array (As calculation algorithm DFS, that sends vertex each time).
The computers calculates the length of maximal clique 
From an edge that was sent to him.
The server receives databases from each of computer and updates the length of maximal clique as needed.
In the event, edgeis not relevant and shorter then max clique, the server avoids sending. 

A server is an instance of a computer program that accepts and responds to requests made by another program, known as a client. 
The computers approached to server through a computer network to request information stored on the server.
Servers are used to manage network resources. 
Some servers are committed to a specific task, often referred to as dedicated. As a result, there are a number of dedicated server categories, like print servers, file servers, network servers and database servers.
Because they are commonly used to deliver services that are required constantly, most servers are never turned off. Servers are commonly high-end computers setup to be fault tolerant.

