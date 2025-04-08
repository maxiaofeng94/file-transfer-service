package com.feng.filetransfers.service.impl;

import com.feng.filetransfers.service.IFileTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileTransferServiceTest {

    @Autowired
    private IFileTransferService fileTransferService;

    @Test
    void deleteFileByHashCode() {
        fileTransferService.deleteFileByHashCode(Arrays.asList(
            "9ed0c718b32148124da8ee4cb3a7d460678d0b379e41d189770265532e52c5b8",
            "5ea813a08220cdb584eebde9180ba6226584be3f402b1c0800018c492782fdd2",
            "5eebb9a0bc3c7393e1a353c33611d717b18167354ab53145bd16b0a42267b819",
            "4f1bb0083ab3b732c4411c98b9a6c4d8ab5063588fd42723deea800b1aaaff69",
            "eb4f277b2dcd540b9b3e4a0915ebb72593eb7b1463281ad16e0083a4905ee552",
            "5876446c0fce4ae8330d500d95f4f8c76dd8c050b80e3325f35db1f98485969e",
            "0dc4b2e8bb09e38605537707d57882df8486b0f67b8f89d7e8bdf9f3193443a6",
            "72fcdd26337dd0da01f3e155ce0ec1821bb4b25e6809e4a1a428859309519f90",
            "2c0cc97ec64c1e4111362e1e32e0547fd870e4d9c79ec844c117da583f21b386",
            "db820eac8f9633a74d2546e3bfe86b5cd73cdbf5a698a43f09c579cb1dcc77da",
            "3808966c900eec321b29ba25edb70bef08e0accbb451ab25c82191ea64008817",
            "aeb60f26cfbe23cd23586219a445f030ec3bc989993d9d4b0dd9110319d5d966"
        ));
    }
}