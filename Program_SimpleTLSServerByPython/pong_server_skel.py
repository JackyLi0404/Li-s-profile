import socket
import ssl
import sys
import time

hostname = "localhost"
port = 4433

#Reference to the cnblog
#Posted by 诸子流
#Date: 2018-07-31 18:28
#Retrived from https://www.cnblogs.com/lsdb/p/9397530.html

# Handle application-layer messages (2cr)
def handle(ssock):
    while True:
        # YOUR TASK STARTS HERE
        #...
        #Receive and decode the data from client
        data = ssock.recv(1024)
        readabledata = data.decode()
        #readabledata = conn.recv(1024)
        print("The server receives the data from client 127.0.0.1: " + str(readabledata))
        if readabledata.strip() == "PING":
            #Send back the encoded data
            ssock.send('PONG\n'.encode("utf-8"))
            return

def main():
    # Create a socket and correct context (3cr)
    # ...

    #Verificate certificate with server_cert and server_key
    context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    context.load_cert_chain('server_cert.pem', 'server_key.pem')

    try:
        #create a socket and listen to the client
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind((hostname, port))
        sock.listen(5)
    except:
        print(socket.error)
        sys.exit(1)

    print("Listening.")

    #Wrap the socket to the sslsocket
    with context.wrap_socket(sock, server_side=True) as sslsocket:
        while True:
            conn = None
            #Get the connected socket with ssl server and client's address
            ssock, addr = sslsocket.accept()
            print("The linked address is " + str(addr) + "\n")
            try:
                # Final step to use the context ... (1cr)
                # ...
                ssock.send('Request a message!\n'.encode("utf-8"))
                handle(ssock)
                #means request is fulfilled
                conn = True
            except Exception as e:
                print(e)
            finally:
                if conn:
                    #Close the ssl socket and exit the system
                    ssock.close()
                    sys.exit(0)

if __name__ == '__main__':
    main()
