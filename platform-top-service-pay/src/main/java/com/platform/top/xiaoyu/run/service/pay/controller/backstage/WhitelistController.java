package com.platform.top.xiaoyu.run.service.pay.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.top.xiaoyu.run.service.api.auth.annotaions.ButtonDefine;
import com.platform.top.xiaoyu.run.service.api.auth.annotaions.MenuDefine;
import com.platform.top.xiaoyu.run.service.api.auth.enums.InternalResource;
import com.platform.top.xiaoyu.run.service.api.common.token.SecurityUtil;
import com.platform.top.xiaoyu.run.service.api.common.vo.req.IdReq;
import com.platform.top.xiaoyu.run.service.api.common.vo.resp.PageResp;
import com.platform.top.xiaoyu.run.service.api.pay.constant.PayConstant;
import com.platform.top.xiaoyu.run.service.api.pay.exception.BasePayExceptionType;
import com.platform.top.xiaoyu.run.service.api.pay.vo.WhitelistVO;
import com.platform.top.xiaoyu.run.service.api.pay.vo.req.whitelist.WhitelistInsertReq;
import com.platform.top.xiaoyu.run.service.api.pay.vo.req.whitelist.WhitelistPageReq;
import com.platform.top.xiaoyu.run.service.api.pay.vo.req.whitelist.WhitelistUpdateReq;
import com.platform.top.xiaoyu.run.service.pay.service.IWhitelistService;
import com.top.xiaoyu.rearend.boot.controller.TopBaseController;
import com.top.xiaoyu.rearend.component.swagger.controller.BackstageController;
import com.top.xiaoyu.rearend.log.annotation.ApiLog;
import com.top.xiaoyu.rearend.tool.api.R;
import com.top.xiaoyu.rearend.tool.exception.BizBusinessException;
import com.top.xiaoyu.rearend.tool.util.bean.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 白名单 控制器
 *
 * @author xiaoyu
 */
@RestController
@AllArgsConstructor
@RequestMapping(PayConstant.BACKSTAGE_WHITELIST)
@Api(value = "白名单", tags = "白名单")
@BackstageController
@MenuDefine(name = "白名单", moduleName = "Whitelist", parentCode = "payManage")
public class WhitelistController extends TopBaseController {

	@Autowired
	private IWhitelistService iWhitelistService;

	@PostMapping("/findPage")
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiLog("分页查询")
	@ButtonDefine(name = "分页查询", internal = InternalResource.ADMIN)
	public R<PageResp<WhitelistVO>> findPage(@RequestBody @Validated WhitelistPageReq req) {
		if( req.getPage() <= 0 ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_PAGE);
		}
		if( req.getSize() <= 0 ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_SIZE);
		}
		Page<WhitelistVO> page = new Page<WhitelistVO>(req.getPage(), req.getSize());
		WhitelistVO vo = BeanCopyUtils.copyBean(req, WhitelistVO.class);
		vo.setPlatformId(SecurityUtil.getLoginAccount().getPlatformId());
		return R.data(new PageResp(iWhitelistService.findPage(page, vo)));
	}

	@PostMapping("/findListAll")
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@ApiLog("查询所有")
	@ButtonDefine(name = "查询所有", internal = InternalResource.ADMIN)
	public R<List<WhitelistVO>> findListAll() {
		WhitelistVO vo = new WhitelistVO();
		vo.setPlatformId(SecurityUtil.getLoginAccount().getPlatformId());
		return R.data(iWhitelistService.findListAll(vo));
	}

	@PostMapping("/findDetail")
	@ApiOperation(value = "查询明细", notes = "查询明细")
	@ApiLog("查询明细")
	@ButtonDefine(name = "查询明细", internal = InternalResource.ADMIN)
	public R<WhitelistVO> findDetail(@RequestBody @Validated IdReq req) {
		if(null == req || null == req.getId() || req.getId() == 0) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_ID);
		}
		return R.data(iWhitelistService.findDetail(req.getId(), SecurityUtil.getLoginAccount().getPlatformId()));
	}

	@PostMapping("/insert")
	@ApiOperation(value = "单行新增", notes = "单行新增")
	@ApiLog("单行新增")
	@ButtonDefine(name = "单行新增", internal = InternalResource.ADMIN)
	public R insert(@RequestBody @Validated WhitelistInsertReq req) {
		if( null == req) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_NULL);
		}
		if( StringUtils.isEmpty(req.getIp()) ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_IP);
		}
		if( req.getPort().intValue() <= 0 ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_PORT);
		}
		WhitelistVO vo = BeanCopyUtils.copyBean(req, WhitelistVO.class);
		vo.setPlatformId(SecurityUtil.getLoginAccount().getPlatformId());
		return R.data(iWhitelistService.insert(vo));
	}

	@PostMapping("/update")
	@ApiOperation(value = "单行修改", notes = "单行修改")
	@ApiLog("单行修改")
	@ButtonDefine(name = "单行修改", internal = InternalResource.ADMIN)
	public R update(@RequestBody @Validated WhitelistUpdateReq req) {
		if( null == req) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_NULL);
		}
		if( req.getId().longValue() <= 0 ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_ID);
		}
		if( StringUtils.isEmpty(req.getIp()) ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_IP);
		}
		if( req.getPort().intValue() <= 0 ) {
			throw new BizBusinessException(BasePayExceptionType.PARAM_PORT);
		}

		WhitelistVO vo = BeanCopyUtils.copyBean(req, WhitelistVO.class);
		vo.setPlatformId(SecurityUtil.getLoginAccount().getPlatformId());
		return R.data(iWhitelistService.update(vo));
	}

	@PostMapping("/delBatch")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	@ApiLog("批量删除")
	@ButtonDefine(name = "批量删除", internal = InternalResource.ADMIN)
	public R delBatch(@RequestBody @Validated List<Long> ids) {
		return R.data(iWhitelistService.delBatch(ids, SecurityUtil.getLoginAccount().getPlatformId()));
	}

}
