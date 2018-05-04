const net = require("net");


const HOST = '127.0.0.1';
const PORT = 80;

net.createServer(function(sock) {

	console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);

	// Add a 'data' event handler to this instance of socket
	sock.on('data', function(data) {

		console.log('DATA ' + sock.remoteAddress + ': ' + data);
		// Write the data back to the socket, the client will receive it as data from the server
		sock.write("SERIAL_VALID=1");

	});

	// Add a 'close' event handler to this instance of socket
	sock.on('close', function(data) {
		console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
	});

}).listen(PORT, HOST);

console.log('Server listening on ' + HOST +':'+ PORT);