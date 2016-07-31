package inner;

import testinterface.ResolverInterface;

public class ResolverOuter {
    protected class ResolverImpl implements ResolverInterface {
        private int x;

        public void resolve() {}

        public ResolverImpl() {}
    }

    public int x() {
        return new ResolverImpl().x;
    }
}
