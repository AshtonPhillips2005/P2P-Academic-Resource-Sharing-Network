# P2P Academic Resource Sharing Network

## 📌 Overview
This project is a decentralized peer-to-peer file sharing system built using Java Sockets. Each peer acts as both a client and a server, enabling direct file sharing without a central server.

---

## ⚙️ Features
- Peer-to-peer architecture
- File transfer using Java sockets
- Chunk-based data transmission
- SHA-256 data integrity verification
- Packet loss handling using ACK/NACK
- Multi-peer communication

---

## 📁 Project Structure
- Main.java → Entry point for each peer
- PeerServer.java → Handles incoming connections
- ClientHandler.java → Processes file requests
- PeerClient.java → Requests files from peers
- HashUtil.java → Generates SHA-256 hashes
- TestClient.java → Simulates file requests

---

## 🚀 Setup Instructions

### 1. Compile the project

### 2. Run Peer 1
java Main 5000
### 3. Run Peer 2
java Main 5001
### 4. Request a file
java TestClient 5001
## Author
Ashton Phillips
