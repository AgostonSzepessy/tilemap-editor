# tilemap-editor
A simple tile map editor written in Java.

# Controls

This tilemap editor works with keyboard combinations. The controls are:

Ctrl + N: create a new map. This will bring up a GUI where there are 3 textboxes: "Height", the height of the tilemap, "Width", the width of the tilemap, and "Tile size", the size of the tiles (they have to be square). Enter values for each textbox, and then click "Browse". This will bring up a file dialog, where the tileset image will be selected. Once it is selected, click "Create" and the tilemap will be created.

Ctrl + S: saves the tilemap. If it is not saved yet, then a file dialog will be brought up. The tilemap must be saved in the same directory that the tileset is located in.

Ctrl + O: opens a tilemap.

Ctrl + R: resizes a tilemap. Select either "Upscale" to upscale, or "Downscale" to downscale the map. Fill in each textbox with the amount that the tilemap is to be resized by. Then click resize and the tilemap will be resized.

WASD: Used for moving around the map. Use "W" to move up, "A" to move left, "S" to move down, "and D" to move right.

Ctrl + L: Adds an object layer. Enter the name of the layer and click "OK". A new layer will be created. However, the object layer won't become the active layer.

Ctrl + Shift + S: Switch between tile and object layers.

Mouse scroll: When in the tile layer mode, it scrolls through the tiles, and when in object layer mode, it scrolls through the object layer.

Left click: When in tile layer mode, it places a tile at the mouse location and when in object layer, it places an object at the mouse location.

Right click: When in tile layer mode, it removes the tile under the cursor, and when in object layer it removes the object under the cursor.

# File Format
The tilemap is stored in a plain text file.

The first line contains the name of the tileset, which must be in the same directory as the tilemap file.

The second line contains the size of the tiles. The tiles must be square.

The third line contains the height of tilemap in tiles.

The fourth line contains the width of the tilemap in tiles.

The fifth line contains the number of object layers.

The following lines contain the tilemap itself. The number of lines depends on the height of the tilemap (the third line).

The following lines contain the object layers. The number of lines depends on how many objects are in each layer and how many object layers there are. An object layer is stored like this:

The first line contains the name of the layer.

The second line contains the number of number of objects in that layer.

The following lines depend on how many objects there are in that layer. Each line is made up of 2 numbers, the x and y coordinate of the object.

To see a sample tilemap with comments describing what the lines are, take a look at tilemap-editor/sample-map.map. Everything following a hashtag is a comment. When the tilemap editor writes a map to a file there will not be any comments.

The image must be formatted as follows:
The image must be made up of two rows, each multiples of the tilesize. E.g. if a tile is 64 pixels, then the tileset's height must be 128 pixels and it's width must be 64 * n. The reason for this is because in games there are some tiles that the player can go through, and some that the player is not able to go through. Thus, one of the rows would be a set of tiles that the player could not go through and the other row could be the tiles that the player can go through.
The first tile in the first row must be a transparent image, because wherever the user did not put a tile, the tilemap editor will mark that with a 0 (the index of the first image).
To see a sample tileset, take a look at tilemap-editor/tileset.png.

# Bugs
When the program starts, it thinks Ctrl is pressed. Press Ctrl to fix this.

When Ctrl or Shift are pressed, the program sometimes thinks that it's still pressed. To fix this, press them again. 
