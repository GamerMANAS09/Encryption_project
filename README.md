# Hybrid Encryption System in Java

## Description

A hybrid encryption system combining a custom substitution-based cipher with AES-GCM encryption for enhanced security in Java.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
- [Usage](#usage)
- [Requirements](#requirements)
- [Disclaimer](#disclaimer)
- [License](#license)

## Features

- Custom substitution + XOR cipher for lightweight obfuscation
- AES-256 encryption in GCM mode for strong authenticated encryption
- Deterministic encryption using password-derived keys (PBKDF2)
- Base64 output for easy storage and transmission
- Secure IV handling (GCM standard 12-byte random IV)

## Project Structure

- `CustomEncryptor.java`: Custom cipher (substitution + XOR + shuffle)
- `AESEncryptor.java`: AES-GCM encryption using PBKDF2
- `HybridCipher.java`: Combines custom + AES for hybrid encryption

## How It Works

1. `HybridCipher` encrypts input data using `CustomEncryptor`
2. The intermediate output is then encrypted using `AESEncryptor` with AES-GCM
3. Decryption reverses this process: AES decryption → custom decryption

## Usage

```java
HybridCipher hybrid = new HybridCipher("myStrongPassword");
String encrypted = hybrid.encrypt("Hello, world!");
String decrypted = hybrid.decrypt(encrypted);

System.out.println("Encrypted: " + encrypted);
System.out.println("Decrypted: " + decrypted);
