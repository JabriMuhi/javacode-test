package org.jabrimuhi.javacodetest.controller;

import org.jabrimuhi.javacodetest.dto.request.WalletUpdateRequestDTO;
import org.jabrimuhi.javacodetest.exception.GlobalExceptionHandler;
import org.jabrimuhi.javacodetest.exception.WalletNotFoundException;
import org.jabrimuhi.javacodetest.model.OperationType;
import org.jabrimuhi.javacodetest.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testUpdateWallet_Success() throws Exception {
        WalletUpdateRequestDTO request = new WalletUpdateRequestDTO();
        request.setWalletId(UUID.randomUUID().toString());
        request.setAmount(1000L);
        request.setOperationType(OperationType.DEPOSIT);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wallet_id\": \"" + request.getWalletId() + "\", \"amount\": 1000, \"operation_type\": \"DEPOSIT\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWallet_WalletNotFound() throws Exception {
        WalletUpdateRequestDTO request = new WalletUpdateRequestDTO();
        request.setWalletId(UUID.randomUUID().toString());
        request.setAmount(1000L);
        request.setOperationType(OperationType.DEPOSIT);

        doThrow(new WalletNotFoundException("Wallet not found"))
                .when(walletService).updateBalance(any(UUID.class), any(Long.class), any(OperationType.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wallet_id\": \"" + request.getWalletId() + "\", \"amount\": 1000, \"operation_type\": \"DEPOSIT\"}"))
                .andExpect(status().isNotFound()) // Проверяем статус 404
                .andExpect(MockMvcResultMatchers.content().string("Wallet not found")); // Проверяем сообщение об ошибке
    }

    @Test
    public void testGetBalance_Success() throws Exception {
        UUID walletId = UUID.randomUUID();
        when(walletService.getBalance(walletId)).thenReturn(1000L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallet/" + walletId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000));
    }
}