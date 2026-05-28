package com.condominio.common.seed;
import com.condominio.common.model.Role;
import com.condominio.common.model.User;
import com.condominio.common.repository.RoleRepository;
import com.condominio.common.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
@Component @Profile("dev")
public class DevDataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) return;
        Role admin = getOrCreate("ROLE_ADMIN");
        Role morador = getOrCreate("ROLE_MORADOR");
        Role porteiro = getOrCreate("ROLE_PORTEIRO");
        createUser("admin","admin123",Set.of(admin));
        createUser("morador","morador123",Set.of(morador));
        createUser("porteiro","porteiro123",Set.of(porteiro));
        log.info("Dev seed: admin/admin123 | morador/morador123 | porteiro/porteiro123");
    }
    private Role getOrCreate(String name) { return roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(name))); }
    private void createUser(String u, String p, Set<Role> roles) { User usr = new User(u,passwordEncoder.encode(p)); usr.setRoles(roles); userRepository.save(usr); }
}
