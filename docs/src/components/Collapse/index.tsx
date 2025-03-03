import React, {type ReactNode} from 'react';
import clsx from 'clsx';
import {Details as DetailsGeneric} from '@docusaurus/theme-common/Details';
import type {Props} from '@theme/Details';

import styles from './styles.module.css';

export default function Collapse({...props}: Props): ReactNode {
  return (
    <DetailsGeneric
      {...props}
      className={clsx(styles.collapse, props.className)}
    />
  );
}