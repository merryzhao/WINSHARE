package com.winxuan.ec.admin.controller.subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.subject.Subject;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.subject.SubjectService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 * 
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

	private static final String PAGE_SUFFIX = "subject/";
	private static final String JSP_FILE_BANNER = "/fragment/subject/subject_001";
	private static final String JSP_FILE_TITLE = "/fragment/subject/subject_002";
	private static final String JSP_FILE_CONTENT = "/fragment/subject/subject_003";
	private static final String JSP_FILE_OTHER_FRAGMENT = "/fragment/subject/subject_004";

	@Autowired
	SubjectService subjectService;

	@Autowired
	CmService cmService;

	@Autowired
	CodeService codeService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView subjects(@MyInject Pagination pagination) {

		ModelAndView mav = new ModelAndView("/subject/list");
		mav.addObject("subjects", subjectService.querySubjects(pagination));
		mav.addObject("pagination", pagination);
		mav.addObject("codes", codeService.findByParent(Code.PRODUCT_SORT));
		return mav;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSubject(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "sort", required = true) Long sort) {

		ModelAndView mav = new ModelAndView("/subject/add");
		Subject subject = new Subject();
		subject.setName(name);
		subject.setTitle(title);
		subject.setTagurl(url);
		subject.setDeploy(Subject.DEPLOY_NO);
		subject.setUpdateTime(new Date());
		subject.setSort(new Code(sort));

		subjectService.addSubject(subject);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ModelAndView delSubject(
			@RequestParam(value = "delId", required = true) Long delId) {
		ModelAndView mav = new ModelAndView("/subject/del");
		Subject subject = subjectService.get(delId);
		if (subject == null) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_WARNING);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "该条记录已经被别人删除掉了，请刷新页面");
			return mav;
		} else {
			subjectService.delSubject(subject);
		}
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/floor", method = RequestMethod.GET)
	public ModelAndView floor(
			@RequestParam(value = "editId", required = true) Long editId) {

		ModelAndView mav = new ModelAndView("/subject/floor");
		Fragment fragment = new Fragment();
		fragment.setPage("subject/" + String.valueOf(editId));
		List<Fragment> fragments = cmService.getFragmentsByContext(fragment);

		mav.addObject(ControllerConstant.RESULT_KEY, fragments == null ? 0
				: fragments.size());
		return mav;
	}

	@RequestMapping(value = "/floor", method = RequestMethod.POST)
	public ModelAndView floorSet(
			@RequestParam(value = "editId", required = true) Long editId,
			@RequestParam(value = "floor", required = true) Long floor) {

		ModelAndView mav = new ModelAndView("/subject/floorSet");
		List<Fragment> fragments = new ArrayList<Fragment>();
		long index = 1;
		Fragment fragment = new Fragment();
		fragment.setName(String.valueOf(index));
		fragment.setType(Fragment.TYPE_LINK);
		fragment.setRule(Fragment.RULE_MANUAL);
		fragment.setQuantity(MagicNumber.ONE);
		fragment.setJspFile(JSP_FILE_BANNER);
		fragment.setPage(PAGE_SUFFIX + String.valueOf(editId));
		fragment.setIndex(index++);
		fragment.setImageType(ProductImage.TYPE_ORIGINAL);
		fragments.add(fragment);

		for (Long i = (long) 1; i <= floor && i <= MagicNumber.TEN; i++) {
			Fragment frag = new Fragment();
			frag.setName(String.valueOf(index));
			frag.setType(Fragment.TYPE_LINK);
			frag.setRule(Fragment.RULE_MANUAL);
			frag.setQuantity(MagicNumber.ONE);
			frag.setJspFile(JSP_FILE_TITLE);
			frag.setPage(PAGE_SUFFIX + String.valueOf(editId));
			frag.setIndex(index++);
			frag.setImageType(ProductImage.TYPE_ORIGINAL);
			fragments.add(frag);
			Fragment frag1 = new Fragment();
			frag1.setName(String.valueOf(index));
			frag1.setType(Fragment.TYPE_PRODUCT);
			frag1.setRule(Fragment.RULE_MANUAL);
			frag1.setQuantity(Integer.MAX_VALUE);
			frag1.setJspFile(JSP_FILE_CONTENT);
			frag1.setPage(PAGE_SUFFIX + String.valueOf(editId));
			frag1.setIndex(index++);
			frag1.setImageType(ProductImage.TYPE_SIZE_160);
			fragments.add(frag1);
		}
		Fragment otherSubFrag = new Fragment();
		otherSubFrag.setName(String.valueOf(index));
		otherSubFrag.setType(Fragment.TYPE_LINK);
		otherSubFrag.setRule(Fragment.RULE_MANUAL);
		otherSubFrag.setQuantity(Integer.MAX_VALUE);
		otherSubFrag.setJspFile(JSP_FILE_OTHER_FRAGMENT);
		otherSubFrag.setPage(PAGE_SUFFIX + String.valueOf(editId));
		otherSubFrag.setIndex(index++);
		otherSubFrag.setImageType(ProductImage.TYPE_ORIGINAL);
		fragments.add(otherSubFrag);

		for (Fragment frag : fragments) {
			cmService.saveFragment(frag);
		}
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}
}
