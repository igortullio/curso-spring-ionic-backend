package com.igortullio.cursomc.services;

import com.igortullio.cursomc.domain.Cliente;
import com.igortullio.cursomc.repositories.ClienteRepository;
import com.igortullio.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null){
            throw new ObjectNotFoundException("E-mail não encontrado");
        }

        String newPass = NewPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

        emailService.sendNewPasswordEmail(cliente, newPass);

        clienteRepository.save(cliente);


    }

    private String NewPassword() {
        char[] vet = new char[10];
        for (int i=0; i<10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0) {
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) {
            return  (char) (random.nextInt(26) + 65);
        } else {
            return (char) (random.nextInt(26) + 97);
        }
    }

}
