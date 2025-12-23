// package com.herin.ecommerce.service;

// import com.herin.ecommerce.dto.UserDTO.LoginRequestDTO;
// import com.herin.ecommerce.dto.UserDTO.UserRequestDTO;
// import com.herin.ecommerce.dto.UserDTO.UserResponseDTO;
// import com.herin.ecommerce.exception.BadRequestException;
// import com.herin.ecommerce.model.UserEntity;
// import com.herin.ecommerce.model.UserPrincipal;
// import com.herin.ecommerce.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class AuthService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authenticationManager;
//     private final JWTService jwtService;

//     @Autowired
//     public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
//                        AuthenticationManager authenticationManager, JWTService jwtService) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.authenticationManager = authenticationManager;
//         this.jwtService = jwtService;
//     }

//     /**
//      * Registers a new user.
//      */
//     public UserResponseDTO register(UserRequestDTO userRequestDTO) {
//         // 1. Vérification des doublons
//         if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
//             throw new BadRequestException("Username already exists");
//         }
//         if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
//             throw new BadRequestException("Email already exists");
//         }

//         // 2. Création de l'utilisateur avec des SETTERS (Plus sûr que le constructeur)
//         UserEntity user = new UserEntity();
        
//         user.setUsername(userRequestDTO.getUsername());
//         user.setEmail(userRequestDTO.getEmail());
        
//         // 3. Cryptage explicite du mot de passe
//         String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
//         user.setPassword(encodedPassword);

//         // 4. Sauvegarde
//         userRepository.save(user);
        
//         return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
//     }

//     /**
//      * Authenticates the user.
//      */
//     public String login(LoginRequestDTO loginRequestDTO) {
//         try {
//             // Tentative d'authentification via Spring Security
//             Authentication authentication = authenticationManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(
//                             loginRequestDTO.getIdentifier(),
//                             loginRequestDTO.getPassword()
//                     )
//             );

//             // Si on arrive ici, le mot de passe est correct
//             UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//             UserEntity user = principal.getUser();

//             // Génération du token
//             return jwtService.generateToken(user.getUsername());

//         } catch (Exception e) {
//             // Affiche l'erreur réelle dans les logs backend (très important pour débugger)
//             e.printStackTrace(); 
//             throw new BadRequestException("Invalid username or password");
//         }
//     }
// }

package com.herin.ecommerce.service;

import com.herin.ecommerce.dto.UserDTO.LoginRequestDTO;
import com.herin.ecommerce.dto.UserDTO.UserRequestDTO;
import com.herin.ecommerce.dto.UserDTO.UserResponseDTO;
import com.herin.ecommerce.exception.BadRequestException;
import com.herin.ecommerce.model.UserEntity;
import com.herin.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user.
     */
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        // 1. Vérification des doublons
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // 2. Création de l'utilisateur avec des SETTERS (Plus sûr que le constructeur)
        UserEntity user = new UserEntity();
        
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        
        // 3. Cryptage explicite du mot de passe
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        user.setPassword(encodedPassword);

        // 4. Sauvegarde
        userRepository.save(user);
        
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    /**
     * Authenticates the user by searching for email OR username, then validating password.
     * Removed AuthenticationManager dependency and added direct password validation
     */
    public String login(LoginRequestDTO loginRequestDTO) {
        try {
            // 1. Chercher l'utilisateur par email OU username (utilise findByUsernameOrEmail)
            UserEntity user = (UserEntity) userRepository.findByUsernameOrEmail(
                    loginRequestDTO.getIdentifier(),
                    loginRequestDTO.getIdentifier()
            ).orElseThrow(() -> new BadRequestException("Invalid username or password"));

            // 2. Vérifier le mot de passe avec BCrypt (direct password matching)
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
                throw new BadRequestException("Invalid username or password");
            }

            // 3. Générer et retourner le token JWT
            String token = jwtService.generateToken(user.getUsername());
            return token;

        } catch (BadRequestException e) {
            // Re-throw les BadRequestException directement
            throw e;
        } catch (Exception e) {
            // Log toute autre exception pour le debugging
            e.printStackTrace(); 
            throw new BadRequestException("Invalid username or password");
        }
    }
}
