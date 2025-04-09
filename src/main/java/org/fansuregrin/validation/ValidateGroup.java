package org.fansuregrin.validation;

import jakarta.validation.groups.Default;

public interface ValidateGroup extends Default {
    interface Crud extends ValidateGroup {
        interface Create extends Crud {
            interface Register extends Create {}
        }

        interface Query extends Crud {
            interface Login extends Query {}
        }

        interface Update extends Crud {
            interface GeneralInfo extends Update {
                interface Self extends GeneralInfo {}
                interface Other extends GeneralInfo {}
            }
            interface Password extends Update {
                interface Self extends Password {}
                interface Other extends Password {}
            }
        }

        interface Delete extends Crud {}
    }
}
