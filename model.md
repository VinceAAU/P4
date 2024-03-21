fn add(a: int, b: int) -> int{
    return a + b;
}

var x: int = add(1, 2)

print(x) //Prints to stdout, probably


import SimpleRoomGenerator //It is implied that it is builtin

struct Enemy {
    health: int = 0;
    enemy_type: type = none;
}

MAP.width = 200
MAP.height = 200


MAP.seed = SYSTEM.time().asMillis() //this is the default anyway
//int roomCount, int minRoomSize, int maxRoomSize
MAP.roomGenerator = SimpleRoomGenerator;

MAP.roomGenerator.roomCount = 10
MAP.roomGenerator.minRoomSize = 15
MAP.roomGenerator.maxRoomSize = 30

MAP.generateRooms()

MAP.put(MAP.rooms[0].centre, PLAYER)

var orc: Enemy
orc.health = 50..100

MAP.put(MAP.centre + (0, 5), orc)

OUTPUT.format = "json"
OUTPUT.path = "C:\Users\carl\Desktop\output.json"
// WOW, MUCH COMMENT. THIS IS A COMMENT!! WE <3 OUR SUPERVISOR AND MODERATOR


if x % 2 == 0 {
    noop;
} else {
    assassinate former president Bush
}


height_map: int[][] = [
    [0, 1, 2, 3],
    [4, 5, 6, 7],
    [8, 9, 10, 11]
]

var this_is_random: int = 0..MAP.height

var playerName: string = "John Jackson"

var shouldLoop: bool = true
// Potentially implement break and continue
while shouldLoop {
    consider assassinating another former president
}