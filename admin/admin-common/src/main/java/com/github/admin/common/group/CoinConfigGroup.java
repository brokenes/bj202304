package com.github.admin.common.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

public interface CoinConfigGroup extends Default {

    @GroupSequence({
            //币名称
            AddGroup.CoinName.class,
            //中文名称
            AddGroup.CoinCnName.class,
            //最小提币手续费
            AddGroup.MinTxFee.class,
            //最大提币手续费
            AddGroup.MaxTxFee.class,
            //单位
            AddGroup.Unit.class,
            //状 态
            AddGroup.Status.class,
            //允许提币
            AddGroup.WithdrawStatus.class,
            //允许充值
            AddGroup.RechargeStatus.class,
            //允许转账
            AddGroup.TransferStatus.class,
            //自动转账
            AddGroup.AutoWithdrawStatus.class,
            //提现阈值
            AddGroup.WithdrawThreshold.class,
            //最小充值数量
            AddGroup.MinRechargeAmount.class,
            //最小提币数量
            AddGroup.MinWithdrawAmount.class,
            //最大提币数量
            AddGroup.MaxWithdrawAmount.class,
            //平台币
            AddGroup.PlatformCoinType.class,
            //合法币种
            AddGroup.LegalType.class,
            //冷钱包地址
            AddGroup.ColdWalletAddress.class,
            //矿工手续费
            AddGroup.MinerFee.class,
            //提币精度
            AddGroup.WithdrawScale.class,
            //币种资料链接地址
            AddGroup.CoinInfoLink.class,
            //充值地址
            AddGroup.DepositAddress.class,
            //账户类型
            AddGroup.AccountType.class,
            //币种介绍
            AddGroup.CoinInformation.class
    })
    interface AddGroup{
        interface CoinName{}
        interface CoinCnName{}
        interface Unit{}
        interface Status{}
        interface MinTxFee{}
        interface MaxTxFee{}
        interface WithdrawStatus{}
        interface RechargeStatus{}
        interface TransferStatus{}
        interface AutoWithdrawStatus{}
        interface WithdrawThreshold{}
        interface MinWithdrawAmount{}
        interface MaxWithdrawAmount{}
        interface MinRechargeAmount{}
        interface PlatformCoinType{}
        interface LegalType{}
        interface ColdWalletAddress{}
        interface MinerFee{}
        interface WithdrawScale{}
        interface CoinInfoLink{}
        interface CoinInformation{}
        interface AccountType{}
        interface DepositAddress{}
    }

    @GroupSequence({
            UpdateGroup.Id.class,
            //币名称
            UpdateGroup.CoinName.class,
            //中文名称
            UpdateGroup.CoinCnName.class,
            //最小提币手续费
            UpdateGroup.MinTxFee.class,
            //最大提币手续费
            UpdateGroup.MaxTxFee.class,
            //单位
            UpdateGroup.Unit.class,
            //状 态
            UpdateGroup.Status.class,
            //允许提币
            UpdateGroup.WithdrawStatus.class,
            //允许充值
            UpdateGroup.RechargeStatus.class,
            //允许转账
            UpdateGroup.TransferStatus.class,
            //自动转账
            UpdateGroup.AutoWithdrawStatus.class,
            //提现阈值
            UpdateGroup.WithdrawThreshold.class,
            //最小充值数量
            UpdateGroup.MinRechargeAmount.class,
            //最小提币数量
            UpdateGroup.MinWithdrawAmount.class,
            //最大提币数量
            UpdateGroup.MaxWithdrawAmount.class,
            //平台币
            UpdateGroup.PlatformCoinType.class,
            //合法币种
            UpdateGroup.LegalType.class,
            //冷钱包地址
            UpdateGroup.ColdWalletAddress.class,
            //矿工手续费
            UpdateGroup.MinerFee.class,
            //提币精度
            UpdateGroup.WithdrawScale.class,
            //币种资料链接地址
            UpdateGroup.CoinInfoLink.class,
            //充值地址
            UpdateGroup.DepositAddress.class,
            //账户类型
            UpdateGroup.AccountType.class,
            //币种介绍
            UpdateGroup.CoinInformation.class
    })

    interface UpdateGroup{
        interface Id{}
        interface CoinName{}
        interface CoinCnName{}
        interface Unit{}
        interface Status{}
        interface MinTxFee{}
        interface MaxTxFee{}
        interface WithdrawStatus{}
        interface RechargeStatus{}
        interface TransferStatus{}
        interface AutoWithdrawStatus{}
        interface WithdrawThreshold{}
        interface MinWithdrawAmount{}
        interface MaxWithdrawAmount{}
        interface MinRechargeAmount{}
        interface PlatformCoinType{}
        interface LegalType{}
        interface ColdWalletAddress{}
        interface MinerFee{}
        interface WithdrawScale{}
        interface CoinInfoLink{}
        interface CoinInformation{}
        interface AccountType{}
        interface DepositAddress{}
    }
}
