/**
 * 根据用户角色设置body类名，用于控制菜单显示
 * @param {string} role 用户角色（'teacher'、'responsible_teacher'或其他）
 */
export function setRoleClassName(role) {
  // 先移除所有角色相关类名
  document.body.classList.remove('teacher-role');
  
  // 如果是教师角色或责任教师角色，添加teacher-role类名
  if (role && (role.toLowerCase() === 'teacher' || role.toLowerCase() === 'responsible_teacher')) {
    document.body.classList.add('teacher-role');
  }
} 