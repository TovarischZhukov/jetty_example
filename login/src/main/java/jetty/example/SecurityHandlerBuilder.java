package jetty.example;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("NotNullNullableValidation")
public final class SecurityHandlerBuilder {
  private static final String ROLE_ADMIN = "admin";
  private static final String ROLE_USER = "user";

  private final ConstraintSecurityHandler security = new ConstraintSecurityHandler();

  public final ConstraintSecurityHandler build(Server server,
                                               LoginService loginService) {
    server.addBean(loginService);
    security.setLoginService(loginService);

    final List<ConstraintMapping> constraintMappings = new ArrayList<>();
    constraintMappings.addAll(constraintFullMapping(
        buildConstraint(ROLE_ADMIN),
        Collections.singletonList("/second/example")
    ));

    constraintMappings.addAll(constraintGetMapping(
        buildConstraint(ROLE_USER, ROLE_ADMIN),
        Collections.singletonList("/example")
    ));

    security.setConstraintMappings(constraintMappings);
    security.setAuthenticator(new BasicAuthenticator());
    security.setDenyUncoveredHttpMethods(true);
    return security;
  }

  private static Constraint buildConstraint(String... userRoles) {
    final Constraint starterConstraint = new Constraint();
    starterConstraint.setName(Constraint.__BASIC_AUTH);
    starterConstraint.setRoles(userRoles);
    starterConstraint.setAuthenticate(true);
    return starterConstraint;
  }

  private static Collection<ConstraintMapping> constraintFullMapping(Constraint constraint,
                                                                     Collection<String> paths) {
    return constraintMapping(constraint, paths, "*");
  }

  protected static Collection<ConstraintMapping> constraintGetMapping(Constraint constraint,
                                                                      Collection<String> paths) {
    return constraintMapping(constraint, paths, "GET");
  }

  private static Collection<ConstraintMapping> constraintMapping(Constraint constraint,
                                                                 Collection<String> paths,
                                                                 String method) {
    return paths.stream()
        .map(path -> {
              final ConstraintMapping mapping = new ConstraintMapping();
              mapping.setConstraint(constraint);
              mapping.setPathSpec(path);
              mapping.setMethod(method);
              return mapping;
            }
        ).collect(Collectors.toList());
  }
}
