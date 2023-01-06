package kr.codesquad.controller;

import kr.codesquad.domain.Result;
import kr.codesquad.domain.lotto.*;
import kr.codesquad.view.InputView;
import kr.codesquad.view.OutputView;

public class LottoController {

    public void run() {
        int money = requestMoney();
        LottoShop lottoShop = new LottoShop();
        Lottos lottos = purchaseLotto(lottoShop, money);
        WinLotto winLotto = makeWinLotto();
        calculateResult(lottos, winLotto, money);
    }

    private int requestMoney() {
        OutputView.showRequestTotalPrice();
        return Integer.parseInt(InputView.inputTotalPrice());
    }

    private Lottos purchaseLotto(LottoShop lottoShop, int money) {
        OutputView.showRequestManualLottoAmount();
        int manualLottoCount = InputView.inputManualLottoAmount();
        OutputView.showRequestManualLottoNumbers(manualLottoCount);
        int totalLottoCount = money / Lotto.LOTTO_PRICE;
        Lottos lottos = lottoShop.buyLotto(totalLottoCount, manualLottoCount);
        OutputView.showLottoAmount(totalLottoCount, manualLottoCount);
        OutputView.showLottoNumbers(lottos);
        return lottos;
    }

    private WinLotto makeWinLotto() {
        OutputView.showRequestWinNumber();
        Lotto winLotto = new Lotto(InputView.inputLotto());

        OutputView.showRequestBonusBall();
        LottoNumber bonusBall = new LottoNumber(InputView.inputBonusBall());
        return new WinLotto(winLotto, bonusBall);
    }

    private void calculateResult(
            Lottos lottos,
            WinLotto winLotto,
            int money
    ) {
        OutputView.showResultStatistics();

        Result result = new Result();
        result.addMatchCount(lottos, winLotto);
        OutputView.showLottoListResult(result);

        double sum = result.getProfit();
        printTotalProfit(sum, money);

    }

    private void printTotalProfit(double sum, int money) {
        OutputView.showProfitResult(sum, money);
    }

}
