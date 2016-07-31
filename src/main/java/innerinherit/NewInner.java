package innerinherit;

import inner.ResolverOuter;
import testinterface.ResolverInterface;

public class NewInner extends ResolverOuter {
    public ResolverInterface resolver() {
        return new ResolverImpl();
    }
}
