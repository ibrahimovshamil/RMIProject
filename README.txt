Since I uploaded it late, I tried to make it fanicer a bit.
Basically to be able to run the program you need to open at least 4 terminals

1) cd to bin folder and
rmic RMIServer.UserServer
rmic RMIServer.UserClient
rmiregistry

2)cd to bin folder and
java RMIServer.UserServerDriver

3) cd to bin folder and
java RMIClient.UserClientDriver A
// this A is the name of the User which will be used later on.
// now u need to enter the timeout(int) for the user A.  For eg.
5 
// then enter

4) cd to bin folder and 
java RMIClient.UserClientDriver B
// this B is the name of the User which will be used later on.
// now u need to enter the timeout(int) for the user B.  For eg.
3

// Basically you need to open new client every time and by giving their timeouts 
// program will try to match them and send handshakes to each other.
// Also terminals show the flow of the program. 

Best Regards Shamil Ibrahimov