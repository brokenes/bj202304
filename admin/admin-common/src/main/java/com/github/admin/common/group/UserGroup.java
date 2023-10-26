package com.github.admin.common.group;

import javax.validation.GroupSequence;

public interface UserGroup {

    @GroupSequence({
            AddGroup.UserName.class,
            AddGroup.NickName.class,
            AddGroup.Password.class,
            AddGroup.Confirm.class,
            AddGroup.Phone.class,
            AddGroup.Email.class,
    })
    interface AddGroup{
        interface UserName{}
        interface NickName{}
        interface Password{}
        interface Confirm{}
        interface Phone{}
        interface Email{}

    }

    interface UpdateGroup{
        interface Id{}
        interface UserName{}
    }
}
