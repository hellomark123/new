package com.seo.config.beetl;

import com.seo.config.beetl.fun.Fun;
import com.seo.utils.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void initOther() {

		groupTemplate.registerFunctionPackage("tool", new ToolUtil());
		groupTemplate.registerFunction("fn.random", new Fun());
	}

}
