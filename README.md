# FileEncryptor
A very basic file encryption application
# External Library Used
Apache Commons Lang 3.7
# Functionality
Encrypts any text file which can be opened in notepad
# Encryption Technique: 
1.Encryption string is converted into an array of integer corresponding to its index in English alphabet.  
  Eg -> Encryption string "hello" will be converted to integer array : [7,4,11,11,14] as h -> 7th index, e -> 4th index and so on.  
2.Index of each character in the message is added with the index in integer array generated above. Once the integer array is exhausted it   starts again from index 0.  
  Eg -> If Message is "Hi, how are you?" and encryption string is "hello" encryption will be as follows  
  Hello -> [7,4,11,11,14]  
  Hi, how are you? -> 7|8|,| |7|14|22| |0|17|4| |24|14|20|?|  
  7+7 -> 14 -> O  
  4+8 -> 12 -> m  
  , -> ,  
  space -> space  
  11+7 -> 18 -> s  
  11+14 -> 25 -> z  
  14+22 -> 36 -> 25 + 11 - 1 -> k  
  space -> space  
  7+0 -> 7 -> h  
  4+17 -> 21 -> v  
  11+4 -> 15 -> p  
  space -> space  
  11+24 -> 35 -> 25 + 10 - 1 -> j  
  14+14 -> 28 -> 25 + 3 - 1 -> c  
  7+20 -> 27 -> 25 + 2 - 1 -> b  
  ? -> ?  
  Encrypted Message : Om, szk hvp jcb?  
# Decryption Technique:
Similar to encryption, only differece is that the integer array generated from the decyption string is subtracted element by element from each corresponding index of character in encrypted string.
