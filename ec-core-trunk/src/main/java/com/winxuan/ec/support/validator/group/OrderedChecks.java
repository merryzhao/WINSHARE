/*
 * @(#)OrderedChecks.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.validator.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * description
 * @author  huangyixiang
 * @version 2011-8-18
 */
@GroupSequence(value={Default.class,LogicChecks.class})
public interface OrderedChecks {

}
