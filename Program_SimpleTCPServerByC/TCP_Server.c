#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
#include <pthread.h>
#include <dirent.h>
#include <inttypes.h>

#define MAX_LEN_FILE 256
#define MAX_LEN_BUFFER 1024
#define MAX_THREAD 20

typedef struct configuration{
    int32_t ip;
    int16_t port;
    char file_path[MAX_LEN_FILE];
}config;

void get_configuration(char* argv, config* con){
    FILE *file_stream = fopen(argv, "r+");
    if (file_stream == NULL) {
        perror("file not exist");
        exit(1);
    }

    fread(&con -> ip, sizeof(int32_t), 1, file_stream);
    fread(&con -> port, sizeof(int16_t), 1, file_stream);
    while(fgets(con -> file_path, MAX_LEN_FILE, file_stream)!=NULL){}

    fclose(file_stream);
}

void error_func(int fd){
    unsigned char error_mes[9] = {0xf0,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    if(send(fd, error_mes, 9, 0) == -1){
        printf("Fail to send data!\n");
        exit(1);
    }
}

typedef struct message{
    unsigned char mes_type;
    unsigned char if_c;
    unsigned char if_r;
    unsigned long payload_length;
    unsigned char* payload;
}mes;

void get_type_of_mes(unsigned char rec_data, mes * my_mes){

    my_mes -> mes_type = ((rec_data & (1 << 7))|(rec_data & (1 << 6))|(rec_data & (1 << 5))|(rec_data & (1 << 4)) >> 4);

    my_mes -> if_c = (rec_data & (1 << 3)) >> 3;

    my_mes -> if_r = (rec_data & (1 << 2)) >> 2;
}

void get_length_of_mes(unsigned char* rec_len, mes * my_mes){
    for(int i = 0; i < 8; i++){
        unsigned long the_byte = rec_len[i];
        my_mes -> payload_length += (the_byte << (8 * (7 - i)));
    }
}

int power(int x, int y){
    for(int i = 1; i < y; i ++){
        x = x * x;
    }
    return x;
}

typedef struct file_name{
    size_t name_count;
    size_t name_length[MAX_LEN_FILE];
    size_t total_name_length;
    char filename[MAX_LEN_FILE][100];
    char file_p[MAX_LEN_FILE];
}fn;

fn client_file;//Global variable, used in directory listing functionality

void getFileName(char * dirPath, fn* client_f){
    DIR * ptr = opendir(dirPath);
    struct dirent *directory;
    memset(client_f, 0, sizeof(fn));

    if(ptr == NULL){
        printf("Dir goes wrong!\n");
        return;
    }
    while((directory = readdir(ptr)) != NULL){
        if(strcmp(directory->d_name,".")==0 || strcmp(directory->d_name,"..")==0 || strcmp(directory->d_name,"thisisadirectory")==0){
            continue;
        }
        strcpy(client_f -> filename[client_f -> name_count],directory->d_name+0);
        client_f -> name_length[client_f -> name_count] = strlen(directory->d_name);
        client_f -> total_name_length = client_f -> total_name_length + strlen(directory->d_name);
        client_f -> name_count ++;
    }
    closedir(ptr);
}

void* worker_fun(void* arg) {
	int fd = *(int*) arg;
    unsigned char buffer[MAX_LEN_BUFFER] = {0};

    while(1){
        int recv_len = recv(fd, buffer, MAX_LEN_BUFFER, 0);
        if(recv_len == -1){
            printf("Fail to receive data!\n");
            exit(1);
        }

        if(recv_len > 0){
            mes my_mes;
            my_mes.mes_type = '\0';
            my_mes.if_c = '\0';
            my_mes.if_r = '\0';
            my_mes.payload_length = 0;
            my_mes.payload = &buffer[9];

            get_type_of_mes(buffer[0] ,&my_mes);
            unsigned char len_buf[8];
            memcpy(len_buf, buffer + 1, 8);
            get_length_of_mes(len_buf, &my_mes);

            if(my_mes.mes_type == 0x00){//Echo functionality
                buffer[0] = 0x10;
                buffer[0] = (buffer[0]) | (my_mes.if_c << 3);
                buffer[0] = (buffer[0]) | (my_mes.if_r << 2);
                if(send(fd, buffer, recv_len, 0) == -1){
                    printf("Fail to send data!\n");
                    exit(1);
                }
                break;
            }
            else if(my_mes.mes_type == 0x20){//Diretory Listing functionality
                if(client_file.name_count == 0){
                    buffer[0] = 0x30;
                    buffer[0] = (buffer[0]) | (my_mes.if_c << 3);
                    buffer[0] = (buffer[0]) | (my_mes.if_r << 2);
                    for(int i = 1; i < 10; i++){
                        buffer[i] = 0x00;
                    }
                    if(send(fd, buffer, 10 , 0) == -1){
                        printf("Fail to send data!\n");
                        exit(1);
                    }
                    break;
                }
                else{
                    //return header
                    size_t actual_file_len = client_file.name_count + client_file.total_name_length;
                    buffer[0] = 0x30;
                    buffer[0] = (buffer[0]) | (my_mes.if_c << 3);
                    buffer[0] = (buffer[0]) | (my_mes.if_r << 2);

                    //Determine the lenght of payload
                    size_t power_c = 0;
                    size_t temp_len1 = actual_file_len;
                    while(temp_len1/256){
                        temp_len1 = temp_len1 / 256;
                        power_c ++;
                    }
                    if(power_c > 8){
                        error_func(fd);
                        break;
                    }
                    else if(power_c == 0){
                        for(int i = 1; i < 8; i++){
                            buffer[i] = 0x00;
                        }
                        buffer[8] = (unsigned char)actual_file_len;
                    }
                    else{
                        size_t temp_len2 = actual_file_len;
                        for(int i = 0; i < power_c; i++){
                            buffer[8-i] = 0xff;
                            temp_len2 = temp_len2 - power(256, (power_c - i));
                        }
                        buffer[8 - power_c] = (unsigned char)temp_len2;
                        for(int i = 1; i < (8 - power_c); i++){
                            buffer[i] = 0x00;
                        }
                    }

                    //Determine the payload
                    size_t ptr_c = 9;
                    for(size_t i = 0; i <  client_file.name_count; i ++){
                        for(size_t j = 0; j < client_file.name_length[i] + 1; j++){
                            if(j == client_file.name_length[i]){
                                buffer[ptr_c] = 0x00;
                            }
                            else{
                                buffer[ptr_c] = (unsigned char)client_file.filename[i][j];
                            }
                            ptr_c ++;
                        }
                    }

                    //send back
                    if(send(fd, buffer, 1 + 8 + actual_file_len , 0) == -1){
                        printf("Fail to send data!\n");
                        exit(1);
                    }
                    break;
                }
            }
            else if(my_mes.mes_type == 0x40){//File Size funcionality
                int if_match = 0;
                for(size_t i = 0; i < client_file.name_count; i ++){
                    if(strcmp(client_file.filename[i], (char*)my_mes.payload) == 0){
                        if_match = 1;
                        char the_source[MAX_LEN_BUFFER];
                        strcpy(the_source, client_file.file_p);
                        the_source[strlen(client_file.file_p)] = '/';
                        strcat(the_source, client_file.filename[i]);
                        FILE  *the_file = fopen(the_source,"r+");
                        if (the_file == NULL) {
                            perror("file not exist");
                            exit(1);
                        }
                        fseek(the_file,0,SEEK_END);
                        unsigned long length = ftell(the_file);
                        //printf("The file length is: %u.", length);
                        fclose(the_file);
                        //Header
                        buffer[0] = 0x50;
                        buffer[0] = (buffer[0]) | (my_mes.if_c << 3);
                        buffer[0] = (buffer[0]) | (my_mes.if_r << 2);
                        //Payload length
                        for(size_t i = 1; i < 8; i++){
                            buffer[i] = 0x00;
                        }
                        buffer[8] = 0x08;
                        //Payload
                        unsigned long temp_len1 = length;
                        size_t power_c = 0;
                        while(temp_len1/256){
                            temp_len1 = temp_len1 / 256;
                            power_c ++;
                        }
                        if(power_c > 8){
                            error_func(fd);
                            break;
                        }
                        else if(power_c == 0){
                            for(int i = 9; i < 16; i++){
                                buffer[i] = 0x00;
                            }
                            buffer[16] = (unsigned char)length;
                        }
                        else{
                            unsigned long temp_len2 = length;
                            for(int i = 0; i < power_c; i++){
                                buffer[16-i] = 0xff;
                                temp_len2 = temp_len2 - power(256, (power_c - i));
                            }
                            buffer[16 - power_c] = (unsigned char)temp_len2;
                            for(int i = 9; i < (16 - power_c); i++){
                                buffer[i] = 0x00;
                            }
                        }
                        //send back
                        if(send(fd, buffer, 17 , 0) == -1){
                            printf("Fail to send data!\n");
                            exit(1);
                        }
                        break;
                    }
                }
                if(if_match == 0){
                    error_func(fd);
                    break;
                }
            }
            else if(my_mes.mes_type == 0x60){//Retrieve functionality
                error_func(fd);
                break;
            }
            else if(my_mes.mes_type == 0x80){//Shutdown functionality
                if(shutdown(fd, SHUT_RDWR) == -1){
                    printf("Fail to shutdown!\n");
                    exit(1);
                }
                break;
            }
            else if(my_mes.mes_type == 0xf0){//Error functionality
                error_func(fd);
                break;
            }
            else{//Error functionality
                error_func(fd);
                break;
            }
        }
    }
    if(shutdown(fd, SHUT_RDWR) == -1){
        printf("Fail to shutdown!\n");
        exit(1);
    }
    close(fd);
	return NULL;
}

int main(int argc, char** argv) {

	int serversocket_fd = -1;
	struct sockaddr_in address;

    config con;
    get_configuration(argv[1], &con);

    getFileName(con.file_path, &client_file);
    strcpy(client_file.file_p, con.file_path);

    int option = 1;

	serversocket_fd = socket(AF_INET, SOCK_STREAM, 0);
	if(serversocket_fd < 0) {
		printf("Fail to create socket!\n");
		exit(1);
	}
    //printf("Succeed to create socket!\n");

	address.sin_family = AF_INET;
	address.sin_addr.s_addr = con.ip;//The IP address received
    address.sin_port = con.port; //The port used

	if(setsockopt(serversocket_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &option, sizeof(int))){
        printf("Fail to setsockopt!\n");
		exit(1);
    }
    //printf("Succeed to setsockopt!\n");

	if(bind(serversocket_fd, (struct sockaddr*) &address, sizeof(struct sockaddr_in))) {
		printf("Fail to bind!\n");
		exit(1);
	}
    //printf("Succeed to bind!\n");

	if(listen(serversocket_fd, 10)){
        printf("Fail to listen!\n");
        exit(1);
    }
    //printf("Succeed to listen!\n");

    int num_of_thread = 0;
    pthread_t threads[MAX_THREAD];
    int clientsocket_fd[MAX_THREAD];

    while(1){
        clientsocket_fd[num_of_thread] = accept(serversocket_fd, NULL, NULL);

        pthread_create(threads+num_of_thread, NULL, worker_fun, (void*)(clientsocket_fd+num_of_thread));

        num_of_thread++;
    }
    for(int i = 0; i < num_of_thread; i++){
        pthread_join(threads[i], NULL);
    }
    close(serversocket_fd);
	return 0;
}
