package xyz.zyv42.turtech_legacy.admin.utility;

import java.util.Optional;

import xyz.zyv42.turtech_legacy.admin.persistence.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class (@code AuditorAwareImpl) is a utility class that provides
 * a method that gets a username of the user for the auditing purposes
 * using Security Context.
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Provides a username of the user for the auditing purposes with
     * the help of the Security Context.
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }
}