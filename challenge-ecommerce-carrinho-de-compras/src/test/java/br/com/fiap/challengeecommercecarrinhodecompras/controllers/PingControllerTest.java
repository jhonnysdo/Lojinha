package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PingControllerTest {
    @Mock
    private PreAuthorize preAuthorize;
    @InjectMocks
    private PingController pingController;

    @Test
    public void testPingEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pingController).build();
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }
}
