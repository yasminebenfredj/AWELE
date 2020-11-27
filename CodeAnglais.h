typedef struct Position {
       int cells_player[12]; // each cell contains a certain number of seeds
       int cells_computer[12];
       bool computer_play; // boolean true if the computer has to play and false otherwise
       int seeds_player; // seeds taken by the player
       int seeds_computer; // seeds taken by the computer
 }Position ;
